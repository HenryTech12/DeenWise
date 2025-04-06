document.addEventListener("DOMContentLoaded", function () {
  const sidebar = document.getElementById("sidebar");
  const toggleBtn = document.getElementById("sidebarToggle");

  toggleBtn.addEventListener("click", function () {
      sidebar.classList.toggle("active");

      // Toggle button icon
      if (sidebar.classList.contains("active")) {
          toggleBtn.innerHTML = "✖"; // Change to "X"
      } else {
          toggleBtn.innerHTML = "☰"; // Change back to bars
      }
  });
});
