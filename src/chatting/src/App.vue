<template>
  <div id="app">
    유저이름 : <input v-model="userName" type="text">
    내용 : <input v-model="message" type="text" @keyup="sendMessage">
    <hr>
    <div v-for="(item, idx) in recvList" :key="idx">
      <h3>{{item.userName}} :: {{item.content}}</h3>
    </div>
  </div>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  name: 'App',
  components: {
  },
  data() {
    return {
      userName : "",
      message: "",
      recvList: []
    }
  },
  created() {
    this.connect()
  },
  methods: {
    sendMessage(e) {
      if(e.keyCode === 13 && this.userName.trim() !== '' && this.message.trim() !== '') {
        this.send()
        this.message = ''
      }
    },
    send() {
      if(this.stompClient && this.stompClient.connected) {
        const msg = {
          userName: this.userName,
          content: this.message
        }
        this.stompClient.send("/receive", JSON.stringify(msg), {})
      }
    },
    connect() {
      const serverURL = "http://localhost:9000/"
      let socket = new SockJS(serverURL)
      this.stompClient = Stomp.over(socket)
      this.stompClient.connect(
          {},
          frame => {
            this.connected = true
            console.log(frame)
            this.stompClient.subscribe("/send", res => {
              this.recvList.push(JSON.parse(res.body))
            })
          },
          error => {
            console.log(error)
            this.connected = false
          }
      )
    }
  }
}
</script>

<style>
</style>
