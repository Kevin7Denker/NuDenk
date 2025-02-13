function showComponent(component) {
    const content = document.getElementById("content");
    if (component === "user") {
        content.innerHTML = `

            <div>
                <h2>Update User Information</h2>
                <form id="updateUserForm" action="/updateUser" method="post">
                    <label for="firstName">First Name:</label>
                    <input
                    type="text"
                    id="firstName"
                    name="firstName"
                    placeholder="${userName}"
                    default="${userName}"
                    required
                />

                <label for="lastName">Last Name:</label>
                <input
                    type="text"
                    id="lastName"
                    name="lastName"
                    default="${userSurname}"
                    placeholder="${userSurname}"
                    required
                />

                <label for="email">Email:</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    default="${userEmail}"
                    placeholder="${userEmail}"
                    required
                />
                <button type="submit">Update</button>
            </form>
            </div>
            <div>
            <h2>Change Password</h2>
            <form>
                <label for="currentPassword">Current Password:</label>
                <input type="password" id="currentPassword" required />
                <label for="newPassword">New Password:</label>
                <input type="password" id="newPassword" required />
                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" id="confirmPassword" required />
                <button type="submit">Change Password</button>
            </form>
            </div>
            <div>
            <h2>Update Address</h2>
            <form id="updateAddressForm" action="/updateAddress" method="post">
                <label for="rua">Street:</label>
                <input
                    type="text"
                    id="rua"
                    name="rua"
                    required
                />

                <label for="numero">Number:</label>
                <input
                    type="number"
                    id="numero"
                    name="numero"
                    required
                />

                <label for="cidade">City:</label>
                <input
                    type="text"
                    id="cidade"
                    name="cidade"
                    required
                />

                <label for="estado">State:</label>
                <input
                    type="text"
                    id="estado"
                    name="estado"
                    required
                />

                <label for="cep">ZIP Code:</label>
                <input
                    type="text"
                    id="cep"
                    name="cep"
                    required
                />
                <button type="submit">Update Address</button>
            </form>
            </div>
        `;
    } else if (component === "options") {
        content.innerHTML = `
            <h2>Options</h2>
            <p>Here you can configure your settings.</p>
            <p> In the future, you will be able to change your theme, language, and other preferences.</p>
        `;
    } else if (component === "terms") {
        content.innerHTML = `
            <div class="terms">
            <h2>Terms and Conditions</h2>
            <h3>Last Updated: 01/17/2025</h3>
            <p>
            Welcome to <b>NuDenk</b>, where your financial journey is as fun as it is educational! Before embarking on this adventure, please read our terms of use carefully. They are like the treasure map that will guide you on our island of financial knowledge!
            By using our digital bank, you agree to the following terms:
            What is <b>NuDenk</b>?
            <b>NuDenk</b> is an interactive platform that simulates the operations of a digital bank, allowing you to learn about personal finance, money management, and investments in a playful and engaging way. Here, you can create your account, make fictitious deposits, perform transfers, and even participate in financial challenges!
            Account Creation
            To start your journey, you will need to create an account. Remember:
            Age: You must be at least 13 years old to sign up.
            False Information: Do not use false information or someone else's information (unless you are doing a learning exercise!).
            Security:
            How It Works?
            Fictitious Money: All the money you see here is just a simulation! Don't worry; you can't get rich (or poor) with it.
            Challenges and Games: Participate in financial challenges that help you learn about budgeting, saving, and investing.
            Transfers: You can send and receive fictitious money between friends (or avatars) within the platform.
            User Responsibilities
            By using <b>NuDenk</b>, you agree to:
            Be kind and respectful to other users.
            Not use the platform for illegal or unethical activities (like pirating or stealing treasures!).
            Not share your credentials with anyone (except maybe your best friend... but only if they promise not to tell!).
            Intellectual Property
            All content, graphics, and games on <b>NuDenk</b> are protected by copyright. You may use these materials only for educational and personal purposes. Do not copy or distribute without our permission (that's like stealing the treasure map!).
            Modifications to the Terms
            We may update these terms from time to time to reflect changes in our practices or the law. When this happens, we will notify you through the platform (or maybe with a flag on the beach!). It is your responsibility to review these terms regularly.
            Contact
            If you have any questions or suggestions about our terms or the platform, please contact us through our customer support. We love hearing from our adventurers! Thank you for choosing <b>NuDenk</b>! We are excited to help you navigate the waters of personal finance while having fun learning. Remember: financial education is an adventure worth taking! These Terms of Use are fictitious and should be adapted as necessary to meet the specific needs of your application and applicable local laws.
            </p>
            </div>
        `;
    }
}

// Chame a função showComponent com o componente "user"
showComponent("user");
