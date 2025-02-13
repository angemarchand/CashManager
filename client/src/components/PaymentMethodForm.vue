<template>
  <v-container>
    <h2 class="text-center my-4">Add payment method</h2>
    <v-form @submit.prevent="submitPaymentMethod">
      <v-text-field
        v-model="paymentMethod.accountId"
        label="Account ID"
        type="number"
        required
      ></v-text-field>

      <v-select
        v-model="paymentMethod.type"
        :items="paymentTypes"
        label="Payment type"
        required
      ></v-select>

      <v-text-field
        v-if="paymentMethod.type === 'CARD'"
        v-model="paymentMethod.creditCardNumber"
        label="Card number"
        required
      ></v-text-field>
      <v-text-field
        v-if="paymentMethod.type === 'CARD'"
        v-model="paymentMethod.cvc"
        label="CVC"
        required
      ></v-text-field>
      <v-text-field
        v-if="paymentMethod.type === 'CARD'"
        v-model="paymentMethod.validityDate"
        type="date"
        label="Date of validity"
        required
      ></v-text-field>

      <v-text-field
        v-if="paymentMethod.type === 'CHECK'"
        v-model="paymentMethod.checkNumber"
        label="Check number"
        type="number"
        required
      ></v-text-field>
      <v-switch
        v-if="paymentMethod.type === 'CHECK'"
        v-model="paymentMethod.cashed"
        label="Cheque cashed"
      ></v-switch>

      <v-btn color="primary" rounded type="submit" block class="mt-4" size="large" >
        Create the payment method
      </v-btn>
    </v-form>

    <div v-if="createdPaymentMethod" class="mt-4">
      <h4 class="text-success text-center">You have successfully chosen the {{paymentMethod.type}} payment method</h4>
    </div>
    <div v-if="error" class="red--text mt-2">
      {{ error }}
    </div>
  </v-container>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import dayjs from "dayjs";

const paymentTypes = ['CARD', 'CHECK']


const paymentMethod = ref({
  accountId: 1,
  type: 'CARD',
  creditCardNumber: '',
  cvc: '',
  validityDate: '',
  checkNumber: null,
  cashed: false,
});

const createdPaymentMethod = ref(null)
const error = ref<string | null>(null)

const submitPaymentMethod = async () => {
  error.value = null;
  try {
    if (paymentMethod.value.validityDate){
      paymentMethod.value.validityDate = dayjs(paymentMethod.value.validityDate).format("YYYY-MM-DDTHH:mm:ss")
    }

    const response = await axios.post('http://localhost:8080/api/payment-methods', paymentMethod.value, {
      headers: { 'Content-Type': 'application/json' }
    });
    console.log("Payment method created:", response.data);
    createdPaymentMethod.value = response.data;
  } catch (err: any) {
    console.error("Error when creating the payment method :", err);
    error.value = err.response?.data || "Error when creating the payment method";
  }
};
</script>

