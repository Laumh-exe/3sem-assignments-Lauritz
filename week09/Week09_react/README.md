2. Please reflect on the following questions

2.1 What is the purpose of the key value, which must be given to individual rows in a react-list
When creating lists of elements with react each elements needs a unique key to keep track of changes in that lists.

2.2 It's recommended to use a unique value from your data if available (like an ID). How do you get a unique value in a map callback, for data without a unique id?
crypto.randomUUID()

2.3 What is the difference(s) between state and props?
Props hold data and can be passed from parent to child and used but not changed. State starts as some value when component mounds and can be changed with some event.

2.4 For which scenarios would you use props, and for which would you use state?
props for passing in arguments to components and state to change data like what we see in a table or a list etc.