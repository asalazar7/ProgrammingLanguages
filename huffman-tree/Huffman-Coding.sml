(* Huffman-Coding.sml - Implementation of a Huffman Tree in ML *)
(* Aaron Kuehler *)
(* An implementation of David Huffman's encoding tree. *)

(*** Environment Configuration ***)
Control.Print.printLength := 200;
Control.Print.printDepth := 200;

(*** Types ***)
exception BadHTree
type 'a node = 'a list * int
datatype 'a HTree = Empty | HT of 'a node * 'a HTree * 'a HTree
datatype X = A|B|C|D|E|F|G

(*** Messages ***)
val message1 = [ "no", "yes", "not", "not", "not", "not", "goodbye",
		 "no", "yes", "not", "no", "yes", "no", "hello", "yes",
	         "when", "why", "why", "how", "how", "yes", "when",
	         "when", "when", "why", "goodbye", "goodbye", "goodbye",
	         "no", "yes", "not", "not", "not", "no", "yes", "not",
	         "yes", "no", "no", "no", "hello", "hello", "goodbye",
	         "never"]
val message2 = [32,23,17,23,34,23,17,32,12,23,32,56,17,33,17,34,17,45]
val message3 = [A,E,A,B,C,A,A,B,D]

(*** Predicates ***)
(* Returns true if t1's weight is less than t2's weight *)
fun HTreeLess (HT ((s1,n1), lt1, rt1), 
	       HT ((s2,n2), lt2, rt2)) = n1 < n2 
  | HTreeLess (Empty, _) = raise BadHTree
  | HTreeLess (_, Empty) = raise BadHTree
	
(*** Sorting Algorithms ***)			 
(* Recursive QuickSort a list of Huffman-Trees *)
fun QuickSort [] = []
  | QuickSort [ht] = [ht]
  | QuickSort (ht::hts) = 
    let
	val lessThanCurrent = (fn x => HTreeLess(x, ht))
	val filt = List.filter	    
    in
	QuickSort (filt lessThanCurrent hts) 
	@ ht :: (QuickSort (filt (not o lessThanCurrent) hts))
    end

(*** Huffman Tree Constructors ***)
(* 
 Constructs a Huffman tree given a node, and the corresponding sub-trees.
*)
fun MakeHTree (node, lt, rt) =  HT (node, lt, rt) 

(* Add tally mark for the symbol in the frequency list *)
fun update (s, nil) = [([s],1)] 
  | update (s1, ([s2], n)::fl)  = if s1=s2 then ([s1],n+1)::fl 
				  else ([s2],n)::update (s1,fl) 
  | update (_,fl) = 
    let 
	exception BadFrequencyList
    in
	raise BadFrequencyList
    end

(* Generate a symbol frequency list. *)
fun MakeFrequencyList (s) = 
    let
				  
	(* Recursive frequency list construction. *)
	fun MakeFrequencyListRecur (nil,fl) = fl 
	  | MakeFrequencyListRecur (s::ss, fl) 
	    = MakeFrequencyListRecur (ss,update (s,fl)) 
    in
	MakeFrequencyListRecur (s,nil)
    end

(*
 Constructs a list of the single node Huffman trees for a given message. 
*)
fun MakeInitialHTrees (m) = 
    let 

	(* Recursive Initial Huffman Tree Constructor. *)
	fun MakeInitialHTreesRecur (nil, tl) = tl
	  | MakeInitialHTreesRecur (freq::freqs, tl) 
	    = MakeInitialHTreesRecur 
		  (freqs, tl@[MakeHTree (freq, Empty, Empty)])
    in 
	MakeInitialHTreesRecur (MakeFrequencyList (m), nil)
    end

(*  
 Merges two Huffman Trees. A new root node is created by appending
 the frequency lists and summing the weights of the two root nodes
 of the trees to be merged. The branches of the new root node are 
 then set to reference the two original original trees. 
*)
fun Merge (t1 as HT ((s1,n1), lt1, rt1), 
		 t2 as HT ((s2,n2), lt2, rt2))
  = HT ((s1@s2, n1+n2), t1, t2)
  | Merge (htree1, htree2) = raise BadHTree

(*
 Recursively merge a list of Huffman-Trees into a list which 
 contains a single tree.
*)
fun MergeTreeList [] = []
  | MergeTreeList ([ht]) = [ht] 
  | MergeTreeList (ht::nht::hts) =
    MergeTreeList (QuickSort (Merge (ht, nht)::hts))

(* Creates a Huffman-Tree for the input message. *)					 
fun MakeHuffmanTree (m) = 
    hd (MergeTreeList (QuickSort (MakeInitialHTrees (m))))

(*** Message Encoding ***)
(*
 Encodes a single word of a message using the Huffman tree of the message 
 to which the word belongs.
*)
fun EncodeWord (word, Empty)  = nil 
  | EncodeWord (_, HT ((_, _), Empty, Empty)) = nil 
  | EncodeWord (word, root as HT ((_,_), 
				  left as  HT ((ls, _), _, _), 
				  right as HT ((rs, _), _, _)))  = 
    let
	val filt = List.filter
	val isWord = (fn x => x = word)
    in

	(* 
	 If the word is in the left branch of the current node, encode our 
         path traversal with a 0.
	*)
	if 0 < List.length(filt isWord ls)  
	then  [0] @ EncodeWord (word, left) 
	else 

	    (* 
	     If the word is in the left branch of the current encode, mark 
             our path traversal with a 1.
	    *)
	    if 0 < List.length (filt isWord rs) 
	    then [1] @ EncodeWord (word, right)

	    (* Otherwise the word is not in our Huffman-Tree. *)
	    else nil
    end
  | EncodeWord (_, ht) = nil 

(* Encodes a message *)
fun Encode ([], ht) = []
  | Encode (w::ws, ht) = EncodeWord (w, ht) @ Encode (ws, ht)

(*** Message Decoding ***)
(* 
 Decodes a list of path traversal instructions to reconstitute the 
 original message 
*)
fun Decode (m, ht) = 
    let
	exception PathTraversalException
	exception UnmatchedDecodingScenario
		  
	(* Recursively Decodes a list of Path Traversal instructions *)
	fun  DecodeRecur (pts, root,  HT ((s, _), Empty, Empty)) 
	  = s @ DecodeRecur (pts, root, root)
	   | DecodeRecur (pt::pts, 
			  root, 
			  current as HT ((_,_), 
					 left as  HT ((ls, _), _, _), 
					 right as HT ((rs, _), _, _))) =
	     if pt = 0 then DecodeRecur (pts, root, left) 
	     else if pt = 1 then DecodeRecur (pts, root, right)
	     else raise PathTraversalException
	   | DecodeRecur (nil, _, _) = [] 
	   | DecodeRecur (_, _, _) = raise UnmatchedDecodingScenario
    in
	DecodeRecur (m, ht, ht)
    end
