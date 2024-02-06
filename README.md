Objective:
The primary objective of this project was the development of a sophisticated website utilizing the Java Spring Boot framework. The overarching aim was to proficiently organize, present, and retrieve information pertaining to books. Additionally, the platform was designed to function seamlessly as an integrated shopping cart, facilitating the streamlined purchase of items.

Implementation:
Methodologically, the project adhered to the principles of CRUD operations and adopted the Model-View-Controller (MVC) architecture. The structuring of the components was meticulously organized, with dedicated folders for templates housing HTML, CSS, and JS files. The Controllers and Beans folders accommodated classes responsible for distinct aspects of the application.

To optimize code readability and streamline development, pivotal dependencies such as Thymeleaf for template rendering and Lombok for code conciseness were incorporated. The security aspect was addressed through the integration of an In-Memory database, with H2 serving as a robust solution.

Technological Stack:
The technological stack encompassed Java Spring Boot, Thymeleaf, Lombok, and H2. These interwoven dependencies synergistically formed the backbone of the application, ensuring a robust and feature-rich foundation.

In summation, the project epitomizes the fusion of cutting-edge technologies, implementing industry best practices in software architecture to deliver a comprehensive web application. This platform adeptly manages book-related functionalities, providing a seamlessly integrated shopping cart for an enhanced user experience.
Result:

![Controller](https://github.com/aniketsha/Books-Org-Project/assets/90695737/f7de2172-219d-439f-b5a7-4777add7e3a1)
This is the `BookController` class which serves as a central component in the web application, managing various HTTP requests related to book operations and user authentication. Through the Spring MVC framework, it handles requests for the application's main page ("/"), secure sections ("/secure"), user login ("/login"), registration ("/register"), and permission-denied scenarios ("/permission-denied"). The controller interacts with a DatabaseAccess bean, employing methods for user registration, book insertion, deletion, and editing. It integrates role-based authorization to restrict certain functionalities to users with the "ADMIN" role. The corresponding Thymeleaf templates are employed to render the user interface for each endpoint, ensuring a seamless and secure user experience.
![image](https://github.com/aniketsha/Books-Org-Project/assets/90695737/aedb148b-fb35-4a87-be68-ade5928a7cea)
This is the screen shot of security config file which is responsible for configuring the security settings of the web application using Spring Security. It defines access controls for various endpoints, integrating role-based authentication and permitting public access to specific resources. Notably, it handles CSRF protection, custom login pages, exception handling, and logout functionality. The class also establishes a BCryptPasswordEncoder bean for secure password encoding during user authentication, contributing to a robust and secure web environment.

