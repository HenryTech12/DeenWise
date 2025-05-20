# 🕌 DeenWise – Interactive Qur'an Learning Platform

DeenWise is a web platform that empowers users to learn the Qur'an interactively with modern tools and guidance. It’s built to support students and tutors through personalized dashboards, video-based lessons, assignments, and real-time chat.

## 🚀 Features

### 👤 Authentication
- Dynamic Islamic greeting based on time of day
- Email/password login
- "Continue with Google" authentication
- Forgot password recovery
- Sign-up for new users

### 📚 Core MVP Functionalities
- 📺 **Watch Video Lessons**
- 📝 **Take Assignments**
- 📖 **Read Qur’an**
- 💬 **Chat with Tutors**

### 🧠 User Roles
- **Students**: Access lessons, assignments, chat, and progress.
- **Tutors**: Manage student engagement, content delivery, and communication.

## 🧩 Tech Stack

### Frontend
- HTML, TailwindCSS, VanillaJS
- Designed and implemented with scalability in mind
- Responsive and user-friendly UI

### Backend 
- Spring Boot (Java)
- Handles authentication, user data, and real-time communication

## 📂 Project Structure (Frontend)

- **Root Level**: Contains public-facing pages (`index.html`, `aboutus.html`, etc.), authentication pages (`login.html`, `signup.html`), and shared authenticated pages like `profile.html` and `settings.html`.
- **`assets/`**: Stores all images, logos, and other static media.
- **`dashboards/`**: Contains the primary landing pages for logged-in users (e.g., `student.html`, `teacher.html`).
- **`studentsPages/`**: Houses HTML files specific to the student's experience after logging in (e.g., viewing their lessons, assignments).
- **`teacher's/`**: Houses HTML files specific to the teacher's experience after logging in (e.g., managing video lessons, assignments).

All pages utilize Tailwind CSS for styling, with a shared configuration for theme colors and fonts. JavaScript is primarily embedded within each HTML file for page-specific functionality and sidebar interactions, with `auth.js` potentially handling Firebase authentication logic globally.
