# Web Marketplace Project

The Web Marketplace project is an online platform designed for facilitating trade of goods and services over the internet. It provides users with the ability to create accounts, add, modify, and delete their listed goods or services, search and view information about other users, and manage their account settings.

## Main Features

1. **User Authentication and Registration**
   - Users can create accounts, log in, and log out securely.
   - Account creation is facilitated with a registration form.

2. **Goods Management**
   - Users can add new goods, specifying details such as name, description, and price.
   - They can also view, edit, and delete their listed goods.

3. **Search and Filter Functionality**
   - Users can search for goods based on various criteria such as category, price range, etc.

4. **Shopping Basket**
   - Users can add goods to a shopping basket for later purchase.

5. **Purchase Process**
   - Users can select goods from the marketplace to purchase.
   - They can view a menu for specific goods before making a purchase.

6. **Password Recovery**
   - Users can request a password reset through a verification code sent to their registered email.

7. **Error Handling and Messaging**
   - The system provides informative error messages to guide users through any issues they may encounter.

## Dependencies

- **GoodsService**: This service manages operations related to goods, including adding, deleting, and retrieving goods.

- **UserService**: Responsible for user-related operations such as registration, authentication, and account management.

- **PurchaseService**: This service handles purchase-related functionalities, including adding goods to the basket, viewing the basket, and making purchases.

- **Database **: The project relies on a database to persistently store user and goods information.

## Usage Notes
- Various endpoints are provided to handle user authentication, registration, password recovery, and account management.
  
- Error messages are displayed in case of invalid input or failed operations.
  
- The project relies on services such as `UserService`, `GoodsService`, `PurchaseService` for carrying out business logic.


## Controller: UserController

This controller handles user-related actions within the Web Marketplace project.

### Endpoints and Actions

1. **Login Page**
   - `GET /`
   - Returns the login page.

2. **Login Form**
   - `GET /login`
   - Returns the login form page.

3. **New User Registration**
   - `GET /new/user`
   - Returns the registration page for a new user.
   
4. **Create User**
   - `POST /add/user`
   - Creates a new user.
   
5. **Get User Information**
   - `GET /get/user`
   - Returns a page to enter the email for finding a user.
   
6. **User Information**
   - `POST /find/user`
   - Retrieves information about a user based on the provided email.

7. **Main Page**
   - `GET /main`
   - Returns the main page of the marketplace.

8. **Get Email Form for Changing Password**
   - `GET /get/send/code`
   - Returns the email form for changing password.

9. **Send Verification Code**
   - `POST /send/code`
   - Sends a verification code to the provided email for changing the password.

10. **Get Verification Code Form**
    - `GET /get/check/code`
    - Returns the form to enter the verification code.

11. **Check Verification Code**
    - `POST /check/code`
    - Checks the provided verification code.

12. **Get Change Password Form**
    - `GET /get/change/password`
    - Returns the form to change the password.

13. **Change Password**
    - `POST /change/password`
    - Changes the user's password.

14. **View Your Account**
    - `GET /get/account`
    - Displays information about the user's account.


---

## Controller: GoodsController

The `GoodsController` is responsible for managing goods-related actions within the Web Marketplace project.

### Endpoints and Actions

1. **New Commodity**
   - `GET /new/goods`
   - Returns the page for adding a new commodity.

2. **Add Commodity**
   - `POST /add/goods`
   - Handles the addition of a new commodity. If there are validation errors, it returns to the addGoods page. Otherwise, it redirects to "your/goods".

3. **Delete Commodity**
   - `POST /delete/goods`
   - Deletes a commodity by its ID. If an exception occurs, it handles the error and redirects to "getYourGoods" with an error message.

4. **Get Your Goods**
   - `GET /your/goods`
   - Retrieves and displays goods specific to the user.

5. **Find Goods**
   - `GET /find/goods`
   - Returns the page for finding goods with a filter.

6. **Get Commodity by Category**
   - `POST /get/found/goods`
   - Retrieves goods based on the provided filter. If the category is blank or the max price is less than or equal to the min price (and max price is not zero), it redirects to "/goods". Otherwise, it displays the found goods.

7. **Sort Goods (Decreasing)**
   - `GET /get/sortGoods/decrease`
   - Sorts goods by price in decreasing order and displays them.

8. **View Goods**
   - `GET /goods`
   - Displays all available goods.

---

## Controller: PurchaseController

The `PurchaseController` manages purchase-related actions within the Web Marketplace project.

### Endpoints and Actions

1. **Get Buy Menu**
   - `GET /get/buy/menu`
   - Retrieves and displays the purchase menu for a specific item.

2. **Add Good to Basket**
   - `GET /get/add/to/basket`
   - Adds a good to the user's basket.

3. **View Basket**
   - `GET /get/basket`
   - Retrieves and displays the user's basket. Handles exceptions by displaying an error message.

4. **Delete Good From Basket**
   - `POST /basket/delete/goods`
   - Deletes a good from the user's basket. Handles exceptions by displaying an error message and redirects to the basket page.
   - 




