const API_URL = `${import.meta.env.VITE_BASE_URL}api`;

class EventDataService {
  retrieveAllEvent() {
    var token = localStorage.getItem('my_tkn');
    return fetch(`${API_URL}/events`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
  }
  retrieveEvent(id) {
    var token = localStorage.getItem('my_tkn');
    return fetch(`${API_URL}/events/${id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
  }
  deleteEvent(id) {
    var token = localStorage.getItem('my_tkn');
    return fetch(`${API_URL}/events/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
    });
  }
  createEvent(newEvent) {
    var token = localStorage.getItem('my_tkn');
    return fetch(`${API_URL}/events`, {
      method: 'POST',
      headers: {
        'content-type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(newEvent),
    });
  }
  updateEvent(id, update) {
    var token = localStorage.getItem('my_tkn');
    return fetch(`${API_URL}/events/${id}`, {
      method: 'PATCH',
      headers: {
        'content-type': 'application/json',
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(update),
    });
  }
  retreiveCategory(id) {
    return fetch(`${API_URL}/events/filter/?categoryId=${id}`, {
      method: 'GET',
      headers: {
        'content-type': 'application/json',
      },
    });
  }
  retreiveAllEventFilter(categoryId, option, time) {
    var token = localStorage.getItem('my_tkn');
    if (time == '') {
      return fetch(
        `${API_URL}/events/filter/?categoryId=${categoryId}&option=${option}`,
        {
          method: 'GET',
          headers: {
            'content-type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        }
      );
    } else {
      return fetch(
        `${API_URL}/events/filter/?categoryId=${categoryId}&option=${option}&time=${time}`,
        {
          method: 'GET',
          headers: {
            'content-type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        }
      );
    }
  }
}
export default new EventDataService();
