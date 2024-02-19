## Stop Data Class
- tells a stop with details : it's name, distanceInKm (distance from origin in km) , distanceInMiles (distance from origin in miles).

## `dummyStops` List
- a/c assignmnet instructions' , hardcoded stops with min. no. 10 are present

## `MainActivity` Class
- similar to c/c++ , it's entry point to my code

## `JourneyApp` Composable
- composable function which resembles the UI of the journey tracking application.

### State Variables
- `currentStopIndex`: Tracks the index of the current stop in the journey.
- `isKmUnit`: Indicates whether the distance unit is in kilometers or miles.
- `totalDistanceCovered`: distance covered in till current stop / distance from current stop to origin.
- `progressList`: List of stops representing the progress made in the journey.
- `currentStop`: The current stop based on the `currentStopIndex`.
- `totalDistanceLeft`: Total distance left to complete the journey.
- `progressState`: Percentage of the journey completed.

### UI Components
- Header Section: Displays the title and a button to switch between kilometers and miles.
- Progress Section: Displays a progress bar and details about the current stop, distance to the next stop, total distance covered ,  total distance left.
- Lazy List for Stops: Displays a list of stops using `StopItem` composable.
- Buttons Section: Contains buttons for navigating to the next stop and stopping the journey.

### Button Actions
- Advancing to the Next Stop: Updates distance variables and progress when the "Next Stop" button is clicked.

## `StopItem` Composable
- Displays details of a `Stop` within a `Card`.

## `ProgressSection` Composable
- Displays a progress bar and details about the current stop, distance to the next stop, total distance covered, and total distance left.

## `MyApp` Composable
- Wraps the main `JourneyApp` composable in a `Surface` with the app's background color.

## `watchPreview` Preview Composable
- A preview function for the app's UI.