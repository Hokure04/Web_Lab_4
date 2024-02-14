<template id="template">
  <div class="content">
    <h1 class="regular-text">ФИО: Каргин Алесандр Максимови</h1>
    <h1 class="regular-text">Группа: P3206</h1>
    <h1 class="regular-text">Вариант: 1950</h1>
  
    <div class="wrapper">
      <div>
        <canvas ref="coordinatesCanvas" id="coordinates-canvas" width="300" height="300"></canvas>
      </div>
  
      <div>
        <div class="form-row">
          <label class="form-label">X:</label>
          <div class="labels">
              <button type="button" class="button" @click="selectXValue(-4)">-4</button>
              <label>-4</label>
              <button type="button" class="button" @click="selectXValue(-3)">-3</button>
              <label>-3</label>
              <button type="button" class="button" @click="selectXValue(-2)">-2</button>
              <label>-2</label>
              <button type="button" class="button" @click="selectXValue(-1)">-1</button>
              <label>-1</label>
              <button type="button" class="button" @click="selectXValue(0)">0</button>
              <label>0</label>
              <button type="button" class="button" @click="selectXValue(1)">1</button>
              <label>1</label>
              <button type="button" class="button" @click="selectXValue(2)">2</button>
              <label>2</label>
              <button type="button" class="button" @click="selectXValue(3)">3</button>
              <label>3</label>
              <button type="button" class="button" @click="selectXValue(4)">4</button>
              <label>4</label>
          </div>
        </div>
  
        <div class="form-row">
          <label for="y-input" class="form-label">Y:</label>
          <input type="text" id="y-input" class="form-input" placeholder="Введите Y">
        </div>
  
        <div class="form-row">
          <label class="form-label">R:</label>
          <div class="labels">
              <button type="button" class="button" @click="r = -4" :class="{ active: r === -4 }">-4</button>
              <label>-4</label>
              <button type="button" class="button" @click="r = -3" :class="{ active: r === -3 }">-3</button>
              <label>-3</label>
              <button type="button" class="button" @click="r = -2" :class="{ active: r === -2 }">-2</button>
              <label>-2</label>
              <button type="button" class="button" @click="r = -1" :class="{ active: r === -1 }">-1</button>
              <label>-1</label>
              <button type="button" class="button" @click="r = 0" :class="{ active: r === 0 }">0</button>
              <label>0</label>
              <button type="button" class="button" @click="r = 1" :class="{ active: r === 1 }">1</button>
              <label>1</label>
              <button type="button" class="button" @click="r = 2" :class="{ active: r === 2 }">2</button>
              <label>2</label>
              <button type="button" class="button" @click="r = 3" :class="{ active: r === 3 }">3</button>
              <label>3</label>
              <button type="button" class="button" @click="r = 4" :class="{ active: r === 4 }">4</button>
              <label>4</label>
          </div>
        </div>
      </div>
    </div>
  
    <div>
      <button id="check-button" class="button" @click="sendButton">check</button>
    </div>
  
    <div class="table-container">
      <table id="results-table">
        <thead>
        <tr>
          <th>x</th>
          <th>y</th>
          <th>r</th>
          <th>result</th>
        </tr>
        </thead>
        <tbody class="custom-tbody">
          <tr v-for="result in results" :key="result.x">
            <td>{{ result.x }}</td>
            <td>{{ result.y }}</td>
            <td>{{ result.r }}</td>
            <td>{{ result.localTime }}</td>
            <td>{{ result.executionTime }}</td>
            <td>{{ result.result ? 'true' : 'false' }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <button id="clear-table-button" class="button clear-table-button" @click="deleteButton">clear</button>
    <button id="logout-button" class="button" @click="logOut">Log out</button>
  </div>
</template>
  
<script>
export default {
  data() {
    return {
      r: 0,
      coefficient: 0,
      token: localStorage.getItem('jwt') || null,
      results: [],
      xValue: null,
      yValue: null,
    };
  },
  watch: {
    r: 'drawElements',
  },
  mounted() {
    if (this.token === null) {
      window.location.href = 'startPage.html';
    }
    this.drawElements();
    document.getElementById("y-input").addEventListener("input", this.validateYInput);
  
    const canvas = this.$refs.coordinatesCanvas;
    canvas.addEventListener("click", this.canvasClick);
  
    this.getDataFromServer();
  },
  methods: {
    logOut() {
      localStorage.removeItem('jwt');
      window.location.href = 'startPage.html';
    },
    getDataFromServer() {
      const token = this.token;
  
      // Отправка GET-запроса на сервер
      fetch('http://localhost:8080/api/v1/result', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
      })
          .then(response => {
            if (response.status === 403) {
              localStorage.removeItem('jwt');
              window.location.href = 'startPage.html';
            }
            return response.json();
          })
          .then(data => {
            // Заполняем массив results данными из GET-запроса
            this.results = data;
          })
          .catch(error => {
            console.error('Error:', error);
          });
    },
    drawElements() {
      console.log('Drawing elements with r:', this.r);
  
      const canvas = this.$refs.coordinatesCanvas;
      const ctx = canvas.getContext('2d');
  
      const coefficient = (canvas.width - 20) / 10;
      this.coefficient = coefficient;
  
      // Очищаем canvas
      ctx.fillStyle = "rgb(256, 256, 256, 1)";
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      ctx.fillRect(0, 0, canvas.width, canvas.height);
  
      // Отрисовка координатных осей
      ctx.beginPath();
      ctx.moveTo(canvas.width / 2, 0);
      ctx.lineTo(canvas.width / 2, canvas.height);
      ctx.moveTo(0, canvas.height / 2);
      ctx.lineTo(canvas.width, canvas.height / 2);
      ctx.strokeStyle = 'black';
      ctx.stroke();
  
      // Перевод отрицательного R в положительное
      const radius = Math.abs(this.r) * coefficient;
  
      ctx.fillStyle = "rgb(51, 153, 255, 0.5)";
  
      // Рисуем прямоугольник
      ctx.fillRect(canvas.width / 2, canvas.height / 2, radius/2, -radius);
  
      // Рисуем 1/4 круга
      ctx.beginPath();
      ctx.arc(canvas.width / 2, canvas.height / 2, radius, Math.PI / 2, Math.PI, false);
      ctx.lineTo(canvas.width / 2, canvas.height / 2);
      ctx.closePath();
      ctx.fill();
  
      // Рисуем треугольник
      ctx.beginPath();
      ctx.moveTo(canvas.width / 2, canvas.height / 2);
      ctx.lineTo(canvas.width / 2 - radius / 2, canvas.height / 2);
      ctx.lineTo(canvas.width / 2, canvas.height / 2 - radius/2);
      ctx.closePath();
      ctx.fill();
  
      ctx.fillStyle = "rgb(0, 0, 0, 1)";
      // Обозначаем значения на осях
      ctx.fontFamily = "Open Sans, sans-serif";
      let fontArgs = ctx.font.split(' ');
      let newSize = '14px';
      ctx.font = newSize + ' ' + fontArgs[fontArgs.length - 1];
  
      // Добавляем обозначения на оси X
      for (let i = -4; i <= 4; i++) {
        ctx.fillText(i, canvas.width / 2 + i * coefficient - 4, canvas.height / 2 + 14);
      }
  
      // Добавляем обозначения на оси Y
      for (let i = -4; i <= 4; i++) {
          if(i === 0){
              continue;
          }
        ctx.fillText(i, canvas.width / 2 - 20, canvas.height / 2 - i * coefficient + 4);
      }
    },
    validateYInput() {
      let yInput = document.getElementById("y-input");
      let yValue = yInput.value;
  
      if (yValue !== '-' && (isNaN(yValue) || parseFloat(yValue) < -3 || parseFloat(yValue) > 3)) {
        alert("Пожалуйста, введите корректное значение Y в диапазоне от -3 до 3");
        yInput.value = '';
      }
    },
    selectXValue(value){
      this.xValue = value;
    },
    canvasClick(event) {
      const canvas = this.$refs.coordinatesCanvas;
      const rect = canvas.getBoundingClientRect();
      const centerX = rect.width / 2;
      const centerY = rect.height / 2;
  
      const coefficient = this.coefficient;
  
      const xValue = (event.clientX - rect.left - centerX) / coefficient;
      const yValue = (centerY - (event.clientY - rect.top)) / coefficient;
  
      console.log('Canvas Clicked - Coordinates:', { xValue, yValue }, 'R:', this.r);
      this.sendDataToServer(xValue, yValue);
    },
    sendButton() {
      const yValue = document.getElementById("y-input").value;
  
      this.sendDataToServer(this.xValue, yValue);
    },
    sendDataToServer(xValue, yValue) {
      xValue = parseFloat(xValue).toFixed(2);
      yValue = parseFloat(yValue).toFixed(2);
      const rValue = this.r;
  
      const token = this.token
  
      // Отправка POST-запроса на сервер
      fetch('http://localhost:8080/api/v1/result', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          x: xValue,
          y: yValue,
          r: rValue,
        }),
      })
          .then(response => {
            if (response.status === 403) {
              localStorage.removeItem('jwt');
              window.location.href = 'startPage.html';
            }
            return response.json();
          })
          .then(data => {
            // Определение цвета точки в зависимости от значения result
            const pointColor = data.result ? 'green' : 'red';
  
            this.results.push({
              x: xValue,
              y: yValue,
              r: rValue,
              result: data.result ? 'true' : 'false'
            })

            // Добавление точки на canvas
            this.drawPointOnCanvas(parseFloat(xValue), parseFloat(yValue), pointColor);
          })
          .catch(error => {
            console.error('Error:', error);
          });
    },
    drawPointOnCanvas(x, y, color) {
      const canvas = this.$refs.coordinatesCanvas;
      const ctx = canvas.getContext('2d');
  
      const coefficient = this.coefficient;
  
      const canvasX = canvas.width / 2 + x * coefficient;
      const canvasY = canvas.height / 2 - y * coefficient;
        
      ctx.beginPath();
      ctx.arc(canvasX, canvasY, 5, 0, 2 * Math.PI);
      ctx.fillStyle = color;
      ctx.fill();
      ctx.closePath();
    },
    deleteButton() {
      const token = this.token;
  
      // Отправка DELETE-запроса на сервер
      fetch('http://localhost:8080/api/v1/result', {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
      })
          .then(response => {
            if (response.status === 403) {
              localStorage.removeItem('jwt');
              window.location.href = 'startPage.html';
            } else if (response.status === 200) {
              // Очистка данных в таблице
              const resultsTable = document.getElementById('results-table').getElementsByTagName('tbody')[0];
              resultsTable.innerHTML = '';
            }
        })
          .catch(error => {
            console.error('Error:', error);
          });
    },
  },
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
  
  #coordinates-canvas {
    margin-left: 10px;
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
  
  #coordinates-canvas {
    margin-left: 5px;
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
  
  #coordinates-canvas {
    margin-left: 2px;
  }
}
  
body {
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
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
}
  
.regular-text {
  color: #3949ab;
  font-size: 35px;
  font-weight: bold;
  font-family: "Courier New", serif;
}
  
/* оболочка для добавления севлева направо */
.wrapper {
  display: flex;
  align-items: center;
}
  
#coordinates-canvas {
  display: flex;
  height: fit-content;
  width: fit-content;
  margin-left: 10px;
}
  
.form-row {
  display: flex;
  margin-bottom: 10px;
  margin-left: 20px;
}
  
.form-label {
  display: inline-block;
  width: 60px;
  font-size: 20px;
  font-weight: bold;
  font-family: 'Courier New', serif;
  margin-right: 10px;
  color: #1a237e;
}
  
.labels {
  color: #00f2ff;
}
  
.form-input {
  flex: 1;
  padding: 5px 10px;
  font-size: 20px;
  border-radius: 10px;
  border: 1px solid #ccc;
  font-family: 'Courier New', serif;
}
  
.button {
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
}
  
.table-container {
  margin-top: 15px;
  max-height: 185px; /* Высота контейнера таблицы */
  overflow: auto; /* Прокрутка при необходимости */
}
  
.table-container::-webkit-scrollbar {
  width: 8px;
  border-radius: 5px;
}
  
.table-container::-webkit-scrollbar-track {
  background-color: #1a237e; /* Цвет фона ползунка */
}
  
.table-container::-webkit-scrollbar-thumb {
  background-color: #3949ab; /* Цвет ползунка */
  border-radius: 5px; /* Округление углов ползунка */
}
  
.table-container::-webkit-scrollbar-thumb:hover {
  background-color: #5c6bc0; /* Цвет ползунка при наведении */
}
  
#results-table {
  border-collapse: collapse;
  font-family: 'Courier New', serif;
  font-size: 18px;
  color: white;
}
  
#results-table th {
  background-color: #1a237e;
  color: #fff;
  font-weight: bold;
  padding: 10px;
  text-align: center;
}
  
#results-table td {
  background-color: #3f51b5;
  padding: 8px;
  text-align: center;
}
  
#results-table tbody tr:nth-child(2n) {
  background-color: #3949ab;
}
  
.custom-tbody {
  background-color: #3949ab;
  text-align: center;
}
  
#results-table tbody tr:nth-child(2n+1) {
  background-color: #303f9f;
}
  
#results-table th,
#results-table td {
  border: 1px solid #1a237e;
}
  
.clear-table-button {
  display: block;
  margin-top: 15px;
  margin-left: auto;
  margin-right: auto;
}
</style>