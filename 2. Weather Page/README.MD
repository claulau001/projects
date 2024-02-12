## Weather Page

If you download this page it will not work as intended because the api key for weather api is most likely to expire. So here is a video of how it works:

https://github.com/claulau001/projects/assets/147533902/acadb867-5ae4-49cf-b010-9f79b927732b

The majority of the functionality is handled by JavaScript. In the HTML, we begin with a DIV element with the ID 'root,' where we dynamically insert elements.

We've implemented a search bar with autocomplete functionality. If you enter the name of a city and press the submit button or hit enter on the keyboard without selecting a suggestion from the autocomplete, the script will attempt to find the city using only the provided name. However, if you select an item from the autocomplete, the script will also search for the longitude and latitude associated with the selected suggestion.

Additionally, we've included a favorite button, allowing users to save cities to localStorage for easy access.
