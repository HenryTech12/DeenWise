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
document.getElementById("sidebarToggle").addEventListener("click", function () {
  let sidebar = document.getElementById("sidebar");
  let mainContent = document.querySelector(".main-content");
  
  if (sidebar.style.left === "-250px") {
      sidebar.style.left = "0";
      mainContent.classList.add("sidebar-active");
  } else {
      sidebar.style.left = "-250px";
      mainContent.classList.remove("sidebar-active");
  }
});