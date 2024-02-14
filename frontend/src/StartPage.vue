<template>
  <div>
    <div class="content">
      <h1 class="regular-text">ФИО: Каргин Алесандр Максимови</h1>
      <h1 class="regular-text">Группа: P3206</h1>
      <h1 class="regular-text">Вариант: 1950</h1>
  
      <div class="wrapper">
        <button @click="setAction('login')">Login</button>
        <button @click="setAction('register')">Register</button>
      </div>
  
      <component :is="currentAction === 'login' ? 'LoginForm' : 'RegisterForm'" />
    </div>
  </div>
</template>
  
<script>
import LoginForm from '@/components/LoginForm.vue';
import RegisterForm from '@/components/RegisterForm.vue';
  
export default {
  data() {
    return {
      currentAction: 'login',
      token: localStorage.getItem('jwt') || null
    };
  },
  mounted() {
    if (this.token !== null) {
      window.location.href = 'mainpage.html';
    }
  },
  methods: {
    setAction(action) {
      this.currentAction = action;
    },
  },
  components: {
    LoginForm,
    RegisterForm
  }
};
</script>
  
<style scoped>
/* Десктопный */
@media screen and (min-width: 1184px) {
  .content {
    padding: 20px;
  }
  
  .wrapper {
    flex-direction: row;
  }
}
  
/* Планшетный */
@media screen and (min-width: 676px) and (max-width: 1183px) {
  .content {
    padding: 15px;
  }
  
  .wrapper {
    flex-direction: column;
  }
}
  
/* Мобильный */
@media screen and (max-width: 675px) {
  .content {
    padding: 10px;
  }
  
  .wrapper {
    flex-direction: column;
  }
}
  
.content {
  background-color: #00f2ff;
  position: relative;
  z-index: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
  overflow: auto;
  padding: 20px;
  color: white;
}
  
.wrapper {
  display: flex;
  align-items: center;
}
  
button {
  background-color: #1a237e;
  color: #fff;
  border: none;
  border-radius: 20px;
  padding: 10px 20px;
  font-size: 1em;
  cursor: pointer;
  font-family: 'Courier New', serif;
  font-weight: bold;
  margin-top: 20px;
  margin: 10px;
}

.regular-text {
  color: #3949ab;
  font-size: 35px;
  font-weight: bold;
  font-family: "Courier New", serif;
}
</style>
  