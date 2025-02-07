import axios, {type AxiosResponse} from 'axios';
import type { User } from '../types/user';

const API_URL = 'http://localhost:8080/api/users';

export default {
  async createUser(user: User): Promise<AxiosResponse<User>> {
    const response = await axios.post(API_URL, user);
    console.log("Response:", response)
    //return { data: response.data, status: response.status };
    return response


  },
  async getUser () {

  },

  async updateUser () {

  },
  async deleteUser () {

  }
};


