My added feature calculates the user's typing speed. The user's speed is displayed in words per minute on the screen and is updated with each key typed. I chose this feature because I thought it was the most useful.

I implemented this by writing a calculateWPM() method. It first calculates the time
elapsed since the prompt was first displayed, using the system nanoTime() method. 
Then it calculates the number of words typed so far. It does this by calculating 
the difference between the length of the typedText string and the length of the 
typedText string with all spaces removed. The function then calculates the words 
per minute by dividing the number of words by the time elapsed and sets the text 
for the corresponding label. The startTime variable is initialized to the current 
time everytime a new prompt is shown, and the calculateWPM() method is called 
everyime a key is typed by the user.

The GUI components I used were:
- a checkbox: I used a checkbox to allow the user decide whether they wanted the prompt to be changed automatically or not. 
- a text area: I used this to display the prompt.
- a radio button: I used radio buttons to allow the user to select a prompt.
- a label: I used a label to display the user's typing speed.
- a button: I used buttons for the virtual keyboard keys.
