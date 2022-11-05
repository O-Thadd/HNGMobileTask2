# HNGMobileTask2
A simple resume app. - In fulfilment of HNG Mobile track task 2.
Summary of my resume and a little about me.

## Features
- Picture of self
- Name, slack username, title
- Support for medium and large screens
- Dark mode
- Multi-language support (English, German, French)

## Codebase Description
A simple android app built natively with Kotlin in android studio.
It is a one-activity, one-fragment app.
Key android development components include, viewmodel, preference datastore, alternative resource files provision.

The viewmodel subserves smooth event handling and maintenance of state across configuration changes.

Preference datastore is used to persist the current theme setting.

With the alternative resource provisioning, app features such as multi language, adaptive layout, dark mode are made possible.
By providing alternative resources, the android system is able to run in various modes. In addition to the defaults, alternative layouts were provided for 3 other screen orientations or sizes, 2 alternative strings sets for German and French, as well as other details such as colour and drawables(pictures, vectors) for dark theme.

## Design
Inspiration was drawn from browsing various designs online. With my little background in graphic design I prepared some of the pictures and icons I used myself.

In medium display or portrait layout mode, the picture and a brief is displayed. Details are accessed via the bottom buttons. The buttons bring up the dialog with the respective details.
In landscape or large display layout mode, the app is displayed on two 'panes'. One pane showing the picture and brief info, the other pane showing a scrollable (if screen real estate demand) list of details.

## Libraries
No third party libraries were used. Libraries and api's used are all stock android. As already outlined in *Codebase description* above.

## Future Features
With more time, some other features to implement include: 
1. Support for more languages.
2. More details to my resume
3. More UI decorations: icons for social media links, etc
4. Job applications management system.

## Link


## Challenges
- Testing for large screens. No physical device available for testing. Online tools had to be used and the debug features were not as good as that in android studio.
- Implementing French. The numerous use of apostrophes in French language causes issues during compilation. The apostrophes need to ba all escaped. Unfortunately, android studio does not provide a clear error message. This added over 1Hr to the total development time.
- Maintaining a proper aspect ratio for profile picture across layouts. Thankfully discovered a cool feature for this with constraintLayout.

## Appetize.io
