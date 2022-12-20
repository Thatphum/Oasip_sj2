import { store } from '../stores/User';

const API_URL = `${import.meta.env.VITE_BASE_URL}api`;

class EventCategoryDataService {
  retrieveAllCategory() {
    return fetch(`${API_URL}/categories`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    });
  }
  retrieveAllCategoryForCreate() {
    return fetch(`${API_URL}/categories/schedule`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    });
  }
  retrieveAllCategoryForFilter() {
    ////////////////////////
    return fetch(`${API_URL}/categories/period`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    });
  }
  updateEvent(id, update) {
    return fetch(`${API_URL}/categories/${id}`, {
      method: 'PATCH',
      headers: {
        'content-type': 'application/json',
        Authorization: `Bearer ${store.accessToken}`,
      },
      body: JSON.stringify(update),
    });
  }
  retrieveCategory(id) {
    return fetch(`${API_URL}/categories/${id}`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    });
  }
}

export default new EventCategoryDataService();
