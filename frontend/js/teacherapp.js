document.addEventListener("DOMContentLoaded", function () {
  const sidebar = document.getElementById("sidebar");
  const toggleBtn = document.getElementById("sidebarToggle");
  const mainContent = document.querySelector(".main-content");

  toggleBtn.addEventListener("click", function () {
      // Toggle sidebar active class
      sidebar.classList.toggle("active");

      // Toggle button icon
      if (sidebar.classList.contains("active")) {
          toggleBtn.innerHTML = "✖"; // Change to "X"
      } else {
          toggleBtn.innerHTML = "☰"; // Change back to bars
      }

      // Toggle sidebar position and main content class
      if (sidebar.style.left === "-250px" || !sidebar.style.left) {
          sidebar.style.left = "0";
          mainContent.classList.add("sidebar-active");
      } else {
          sidebar.style.left = "-250px";
          mainContent.classList.remove("sidebar-active");
      }
  });
});