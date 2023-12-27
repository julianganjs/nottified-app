# Nott-ified&nbsp;&nbsp;<img src="https://github.com/julianganjs/nottified-app/assets/127673790/12af595c-f077-490c-a6d8-13040711d247" height="25px">

This repository contains the source code for the Nott-ified app. Nott-ified is an Android app that acts as a central platform to access various IoT solutions which were developed for a larger project involving smart campuses. The name is derived from a hybrid of the word “notified” and my university's name “Nottingham”.

The objective of the main project is to develop multiple IoT solutions for a smart campus which will improve the efficiency and operations of campus services while enhancing community safety and security. Some areas that are targeted in the project are managing parking spaces, streamlining security clearance procedures at campus entrances, tracking the operating status of campus facilities and offices, and improving operations in the campus cafeteria.

The purpose of Nott-ified is to enable users to submit requests and access helpful information, such as viewing the status of campus facilities, placing and tracking food orders in the cafeteria, and checking the availability of parking spaces and study spaces on campus.

The app was developed using Android Studio, and utilizes XML for each app screen's design along with Java for the functionalities. The app also leverages the XAMPP platform, which provides an easy-to-use control panel for managing Apache, MySQL, and other services. With this, PHP can be used for server-side scripting and MySQL for database management. Data is transferred between the app and database through HTTP requests via various PHP pages.
## Prerequisites
- Android Studio
> Android Studio is required to upload the app to an emulator or to your own Android device.
- XAMPP
> XAMPP is needed to host the MySQL database and PHP pages for the app to work properly.
## Usage
1. Install **Android Studio** (if you haven't). You may follow the guide from this link: https://developer.android.com/studio/install
2. Download the [Nottified](https://github.com/julianganjs/nottified-app/tree/main/Nottified) file from this repository.
3. Open the downloaded file as a project in **Android Studio**, and upload the app to an emulator or your Android device.
4. Install **XAMPP** (if you haven't). You may follow the guide from this link: https://www.wikihow.com/Install-XAMPP-for-Windows
5. Download the PHP pages from the [php](https://github.com/julianganjs/nottified-app/tree/main/php) folder in this repository.
6. Paste the PHP files into the `htdocs` folder inside **XAMPP**'s file directory.
7. Click 'Start' at the MySQL module on the **XAMPP** control panel.
8. Type `localhost` in your default browser and it should direct to the **XAMPP** dashboard.
9. Click on 'phpMyAdmin' to open up the **phpMyAdmin** dashboard for MySQL.
10. Create a database and sample tables.
11. Edit the downloaded PHP pages to match the names of the database and tables that you have created.
12. Launch the Nott-ified app.
## Features
- An online food ordering system for cafeterias, equipped with online payment and notifications for food collection.
- Estimate crowd sizes at campus facilities without heading there physically.
- Check the status of study rooms without heading there physically.
- Register vehicle license plates and view all entry records.
- Check for available parking spots around campus with GPS navigation.
## User Interfaces
- ### Login Screen
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/b973a28a-f838-4542-8bc8-45f805d1c6d4" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/1eadcbce-a923-44d5-909d-54cf63f9bd1f" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/db4e672e-724f-4531-9a11-394eb32d2f0d" height="300vw">
- ### Student Portal
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/776daaf6-da5f-49b5-a9ae-8cdcf46ff63c" height="300vw">
- ### Staff Portal
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/7613548c-c009-4ba3-ae4d-33ea5f41401c" height="300vw">
- ### Online Food Ordering System
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/d746d0d5-7f0d-43e2-87ff-33df9427b38d" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/014a873e-b0d5-4271-afbc-39b423a9c4fb" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/4a6200d1-00d7-471d-8c1d-b947e0666153" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/49a96c9c-6d65-4770-bc7e-b84faf0b7f0f" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/507c534f-18ce-4d64-8979-c9c57a12341d" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/8c078ae2-31d1-4d31-ba3d-d4c24726428f" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/cae45bd7-117f-439e-8d80-bd3f6326117c" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/08b41f66-861a-4545-8246-e8815d4ed014" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/edbc44ca-e48a-4fa6-a558-6d5a6d6c745e" height="300vw">
- ### Crowd Size Checker
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/b2fa0767-e1a4-4fac-92e1-32a186f478c1" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/d27eba47-bd4e-455e-a44f-e7e905932c34" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/725e556a-a293-45b6-aef7-41959ba7ef83" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/75153d5c-d37c-453e-98c0-1e0ff3fa19aa" height="300vw">
- ### Room Status Checker
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/ee33cb70-c75b-4601-8a12-1a89d4e6d7cd" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/3fbd4c5b-3825-4aeb-a052-9bea847084ec" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/37304212-0ac6-46b0-9c90-919a0e64cbad" height="300vw">
- ### Vehicle Registration
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/f147328f-dde0-4a80-b560-1b9ba3bfcb18" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/eddd0eec-4943-4cc6-b946-a97efcea7444" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/55cfa17a-db76-41e9-95f0-695fd8b380dd" height="300vw">
- ### Parking Space Occupancy Checker
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/208907e7-12e3-454b-a16e-ce87f9ac3ff3" height="300vw">
  <img src="https://github.com/julianganjs/nottified-app/assets/127673790/9a701e65-28a4-4042-9397-244a29692a19" height="300vw">
## System Flowchart
<img src="https://github.com/julianganjs/nottified-app/assets/127673790/b5b82999-9820-4a55-b0d2-62e804cb6262" width="900vw">

## License 
This project is licensed under the MIT License.
