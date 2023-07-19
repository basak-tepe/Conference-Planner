<script >
// OPTIONS API
// defineProps({
//   msg: {
//     type: String,
//     required: true
//   }
// })
//
// export default{
//   name:"HelloWorld",
//   data(){
//     return{
//       msg:""
//     }
//   },
//   mounted(){
//     fetch("/api/events/hello")
//         .then((response) => response.text())
//         .then((data)=>{
//           this.msg = data;
//         })
//   }
// }


//COMPOSITION API
// export default {
//   setup() {
//
//     //data
//     const name = ref('HelloWorld');
//     //methods
//     //methods
//     onMounted(() => {
//       fetch("/api/events/hello")
//          .then((response) => response.text())
//          .then((data)=>{
//            this.msg = data;
//          })
//     });
//     return { msg };
//   },
// };


</script>

<template>
    <h1 class="green">{{ msg }}</h1>
</template>

