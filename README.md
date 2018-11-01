# TV Characters Viewer
This project allows us to view a list of tv charactersv using the API from DuckDuckGO.

When you open the project with Android Studio in the Build Variants section you can select to generate the app either to view the characters from "The Simpsons" or from the show "The Wire".


## Phone
When you generate the apk and you run it on a mobile device at the beggining you will get a list of descriptions (since there was no title in the API) of the characters and you can toogle to view the image of the characters (tapping in the top menu).

If you click either on a description or in an image the details of that character will be shown (image + description).

## Tablet
When you generate the apk and you run it on a tablet on your left size you will see a list of character description and on the right side it will be blank at the beggining. If you tap on a description; on the right side the character details (image + description) will be loaded.

## Special features
Whether it's tablet or phone; when the list loads it has an animation. When you are on a phone and tap an item or hit back to go back to the list; and animation is shown.
When the any of the apps runs on a tablet and you tap on an item; a loader is shown before showing the content.

The Wire app for the phone has a local search feature in the toolbar that lets you search of anything in all the descriptions.


## How was this project created
This project uses flavors to create 2 different apps that share different packages, but common code, so I decided to just add the search function in a particular flavor; to illiustrate how though but apps shared code at, at least 90%, but apps in the future can diverge based on different requirements. But as long as both apps shared code; the shared code should be kept in a way that it is re-usable for other apps as much as possible.

The libraries and languages used for this project included:
* Retrofit2: To make HTTP requests to the API.
* Gson: For parsing the Json into objects.
* Dagger2: For dependnecy injection.
* RXJava: To run the Retrofit requests in an async way.
* Google Architecture components (LiveData, ViewModels): So I would be able to implement an MVVM architecture which I like better than MVP becuase it reduces boiler plate code and it is life cycle aware.
* Kotlin and Java: I used Kotlin as much as possible and I did a few classes in Java, because I like how with Kotlin not only can you reduce the possibilities of the million dollar error (NullPointer Exception) but you can reduce the boiler plate code.
* Build variants: To load different queries and in theory different urls.

## What would I improve if I had more time
## Add Tests
Although I didn't have to much time to develop this app (2 days) I would have normally started with a couple of instrumentation tests using Expresso to test the recycler view -> tapping an item and showing the details, another test for the local search, and another test for switching between text and images.
I would had added a test in the ViewModel using JUnit and Mockito for the loading of the characters.

## Add base classes
On bigger source code apps I would had added Base clasess for all my different layers inside my app. So I can re-use code as much as possible.

## Add offline mode
Right now the code is not prepared to handle offline operations, but given more time I would add in the assets the different API's json which would be loaded if the app detects that there is no internet and there is no content cached.

## Add caching
I would also add the cache as suggested in https://futurestud.io/tutorials/retrofit-2-activate-response-caching-etag-last-modified
And I would also use a Room database to handle cache.