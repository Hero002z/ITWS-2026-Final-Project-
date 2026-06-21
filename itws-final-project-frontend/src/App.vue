<script setup lang="ts">
const year = new Date().getFullYear()
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary mb-3">
    <div class="container">
      <RouterLink class="navbar-brand" to="/">
        <i class="fa-solid fa-chess-knight"></i> Chess Hub
      </RouterLink>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <RouterLink class="nav-link" active-class="active" to="/">
              <i class="fa-solid fa-house"></i> Home
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" active-class="active" to="/player">
              <i class="fa-solid fa-user"></i> Players
            </RouterLink>
          </li>
          <li class="nav-item">
            <RouterLink class="nav-link" active-class="active" to="/tournament">
              <i class="fa-solid fa-trophy"></i> Tournaments
            </RouterLink>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="container">
    <RouterView />
  </div>
  <footer class="text-center mt-5 mb-3 text-secondary">
    &copy; {{ year }} - Singidunum University
  </footer>
</template>