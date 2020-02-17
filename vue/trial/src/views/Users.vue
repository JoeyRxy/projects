<template>
  <div>
    <table>
      <tr>
        <th>用户名</th>
        <th>手机号</th>
        <th>余额</th>
      </tr>
      <tr v-for="item in users" :key="item">
        <td>{{item.name}}</td>
        <td>{{item.mobile}}</td>
        <td>{{item.balance}}</td>
      </tr>
    </table>
    <br />
    {{msg}}
  </div>
</template>

<style scoped>
</style>

<script>
export default {
  name: "User",
  data() {
    return {
      msg: "Hello World",
      users: [
        {
          name: "rxy",
          mobile: "15652580228",
          balance: 0
        },
        {
          name: "zs",
          mobile: "12345678901",
          balance: 4
        }
      ]
    };
  }
};
</script>