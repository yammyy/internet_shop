function openNavProfile() {
  document.getElementById("navProfile").classList.remove("closed-nav-profile");
  document.getElementById("navProfile").classList.add("opened-nav-profile");
  document.getElementById("mainContent").classList.add("body-covered");
}

function closeNavProfile() {
  document.getElementById("navProfile").classList.remove("opened-nav-profile");
  document.getElementById("navProfile").classList.add("closed-nav-profile");
  document.getElementById("mainContent").classList.remove("body-covered");
}