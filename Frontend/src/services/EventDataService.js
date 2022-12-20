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
  createEvent(newEvent, file) {
    const formData = new FormData();
    formData.append('event', JSON.stringify(newEvent));
    formData.append('file', file);
    return fetch(`${API_URL}/events`, {
      method: 'POST',
      headers: {
        Accept: '*/*',
        Authorization: `Bearer ${store.accessToken}`,
      },
      body: formData,
    });
  }
  updateEvent(id, update, file) {
    const formData = new FormData();
    formData.append('eventId', id);
    formData.append('event', JSON.stringify(update));
    formData.append('file', file);
    return fetch(`${API_URL}/events`, {
      method: 'PATCH',
      headers: {
        Accept: '*/*',
        Authorization: `Bearer ${store.accessToken}`,
      },
      body: formData,
    });
  }
  retreiveCategory(id) {
    return fetch(`${API_URL}/events/?categoryId=${id}`);
  }
  retreiveAllEventFilter(categoryId, option, time) {
    if (time == '') {
      console.log('ddsadad');
      return fetch(
        `${API_URL}/events/filter/?categoryId=${categoryId}&option=${option}`,
        {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${store.accessToken}`,
          },
        }
      );
    } else {
      return fetch(
        `${API_URL}/events/filter/?categoryId=${categoryId}&option=${option}&time=${time.toString()}`,
        {
          method: 'GET',
          headers: {
            Authorization: `Bearer ${store.accessToken}`,
          },
        }
      );
    }
  }
}

export default new EventDataService();
