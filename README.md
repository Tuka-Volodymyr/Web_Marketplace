# Web_Marketplace

The Marketplace project is a robust e-commerce platform developed using Java and HTML. It provides a seamless and user-friendly interface for buying and selling a wide range of products. Leveraging the power of Java for backend operations and HTML for frontend design, this project offers a reliable and dynamic shopping experience.

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

### Dependencies
- `UserService` for handling user-related operations.
- `GoodsService` for managing goods.

### Usage Notes
- Various endpoints are provided to handle user authentication, registration, password recovery, and account management.
- Error messages are displayed in case of invalid input or failed operations.
- The project relies on services such as `UserService` and `GoodsService` for carrying out business logic.

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

