import {ref} from 'vue';
import userService from '../services/userService';
import type {User} from '../types/user';
import router from "@/router";

export function useUser() {
  const user = ref<User | null>(null);
  const error = ref<string | null>(null);

  const createUser = async (newUser: User) => {
    error.value = null;
    try {
      const response = await userService.createUser(newUser);
      user.value = response.data
      console.log("User: ", user.value)
      if (response.status == 200){
        await router.push({path: 'login'})
      }
    } catch (err: any) {
      error.value = err.response?.data?.message || "Erreur lors de la création de l'utilisateur";
    }
  };

  return { user, error, createUser };
}


