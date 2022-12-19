import { store } from '../stores/User';

const API_URL = `${import.meta.env.VITE_BASE_URL}api`;
class EventDataService {
  retrieveAllEvent() {
    return fetch(`${API_URL}/events`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    });
  }
  retrieveEvent(id) {
    return fetch(`${API_URL}/events/${id}`, {
      method: 'GET',
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    });
  }
  deleteEvent(id) {
    return fetch(`${API_URL}/events/${id}`, {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${store.accessToken}`,
      },
    });
  }
  createEvent(newEvent) {
    return fetch(`${API_URL}/events`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
        Authorization: `Bearer ${store.accessToken}`,
      },
      body: JSON.stringify(newEvent),
    });
  }
  updateEvent(id, update) {
    return fetch(`${API_URL}/events/${id}`, {
      method: 'PATCH',
      headers: {
        'content-type': 'application/json',
        Authorization: `Bearer ${store.accessToken}`,
      },
      body: JSON.stringify(update),
    });
  }
  retreiveCategory(id) {
    return fetch(`${API_URL}/events/?categoryId=${id}`);
  }
  retreiveAllEventFilter(categoryId, option, time) {
    if (time == '') {
      return fetch(
        `${API_URL}/events/?categoryId=${categoryId}&option=${option}`
      );
    } else {
      return fetch(
        `${API_URL}/events/?categoryId=${categoryId}&option=${option}&time=${time}`
      );
    }
  }
}
export default new EventDataService();
