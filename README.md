An application in wich the logged in (oauth2) user can register devices (mobile, tablet, etc.), and can give tracking settings for it.
Tha backend implemented in Spring Boot with a MySQL database, and the admin interface in Angular.
Another application (https://github.com/johnnysta/device-tracker-app-flutter) can be installed on the mobile devices that periodically reads settings data for the given device (identified by Android ID) from backend,
and periodically sends GPS info, timestamp etc. to backend.
Trancking data for a device can be displayed on a Google map in the Angular frontend.
