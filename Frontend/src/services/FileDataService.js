import { store } from '../stores/User';

const API_URL = `${import.meta.env.VITE_BASE_URL}api`;

class FileDataService {
  retrieveFile(id, filename) {
    return fetch(`${API_URL}/file/${id}/${filename}`, {
      method: 'GET',
      headers: {
        'content-type': 'application/json',
        Authorization: `Bearer ${store.refreshToken}`,
      },
    });
  }
}

export default new FileDataService();
