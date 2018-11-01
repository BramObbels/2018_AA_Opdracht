# Application Architecture project 2018

## Maintainers
- Bram Obbels
- Dylan Van Assche

## Setting up a Java EE project in Netbeans 8.2 and Glassfish 4.1.1

1. Install Netbeans and the Glassfish Server 4.1.1.
2. Create a new Java EE project.
3. Add EJB and WAR project in the Wizard (default).
4. Create a normal Java project (Java library) for the bean interfaces.
5. Make your WAR and EJB projects depend on this shared library (Properties, Libraries, add).
6. Add a JDBC connection resource and pool with your DB on the main EE project.
7. Check the empty folders and add an empty file in each one for Git.
8. Add Deployment Descriptor to the WAR project (New, Other..., Web, Standard Deployment Descriptor).
9. Add GlassFish Descriptor to the WAR project for the Security part (New, Other..., GlassFish, GlassFish Descriptor).
9. Add the project to your Git project.

## Adding controllers

1. Select your WAR project.
2. Create a package `controller`.
3. Add a Java class with the name of your controller.
4. Add the Servlet to the XML deployment descriptor with the right URL pattern.


