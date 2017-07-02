# Lidl-PLU-App

Price-Look-Up numbers (PLU) Numbers are used in supermarkets to track prices when an item has no barcode or the bar code can't be scanned or for seasonal items.

This app should help to search, edit and save PLU numbers.

## How to install
Project requires Android Lolipop (5.0) or higher /API Level 21

For now you have to compile the app yourself. Just import the project in Android Studio.
Installation will require permission to install 3rd party apps. Change the settings accordingly.

## How to use the app
The app comes with the latest (**summer 2017**) set of plu numbers for the supermarket chain **Lidl** for the german markets. PLU numbers are localized and change on a regular basis. I try to keep track of them.

______
![Alt text](/docs/app1.png "Layout")

- PLU number is written in **bold** black text for better readability. The item name is next to it.
- Items are sorted in a alphabetical order.
- It features a search bar on top. You can search by an exact PLU Number or by a partial name. Filtered results are ordered by best-match policy.

- LongTap opens a dialog to edit said entry.
- Press the edit button in the bottom right corner to create a new entry

______
![Alt text](/docs/dialog.png "Layout")

- **PLU** - the associated PLU numbers
- **Bezeichnung** - item name
- **Abbruch** - cancel
- **Speichern** - save
- **bin** - deletes all entries with the PLU number

### Important
- PLU numbers are unique in that if you create an item with an existing PLU number it will delete all other entries with the same PLU number
- The data is currently stored locally. Local changes will be lost if you update the app in the future.

## ToDo
- localization for both UI and PLU Number sets.
- Quiz. Training quiz with focus on recently search items
- global database. PLU numbers are currently stored on a local sqlite DB on the device. Makes rolling out update a pain.
- ViewHolder pattern to increase performance
- style. Looks decent, but could be better

**If you want to help your more than welcome to create a pull request!**

______
This app is in no way funded, approved, supervised or in any other fashion associated with Lidl E-Commerce International GmbH & Co. KG or any of its subsidiaries.

I do not claim validty of the provided data. If you intent to use the app at work please make sure you are allowed to do so.
