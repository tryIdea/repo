##form表单
``<form action="/submit-cat-photo">
  <label><input type="radio" name="indoor-outdoor" checked> Indoor</label>
  <label><input type="radio" name="indoor-outdoor"> Outdoor</label>
  <label><input type="checkbox" name="personality" checked> Loving</label>
  <label><input type="checkbox" name="personality"> Lazy</label>
  <label><input type="checkbox" name="personality"> Energetic</label>
  <input type="text" placeholder="cat photo URL" required>
  <button type="submit">Submit</button>
    </form>
    ``


    In computer science, Data types are things that represent various types of data. JavaScript has seven data types which are Undefined, Null, Boolean, String, Symbol, Number, and Object. For example, the Number data type represents numbers.


    // Example
var firstLetterOfFirstName = "";
var firstName = "Ada";

firstLetterOfFirstName = firstName[0];


The equality operator will do its best to convert values for comparison, for example:

   1   ==  1        // true
  "1"  ==  1        // true
   1   == '1'       // true
   0   == false     // true
   0   == null      // false
   0   == undefined // false
 null  == undefined // true

 Like the equality operator, greater than operator will convert data types of values while comparing.

Examples

 5 > 3   // true
 7 > '3' // true
 2 > 3   // false
'1' > 9  // false


switch (num) {
  case value1:
    statement1;
    break;
  case value2:
    statement2;
    break;
...
  case valueN:
    statementN;
    break;
}
case values are tested with strict equality (===).

If you have multiple inputs with the same output, you can represent them in a switch statement like this:

switch(val) {
  case 1:
  case 2:
  case 3:
    result = "1, 2, or 3";
    break;
  case 4:
    result = "4 alone";
}

 If the property of the object you are trying to access has a space in it, you will need to use bracket notation.