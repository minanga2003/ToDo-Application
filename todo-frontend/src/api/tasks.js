
import axios from 'axios';

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export function getLatestTasks(limit = 5) {
  return axios.get(`${API_BASE}/api/tasks?limit=${limit}`);
}
export function createTask(task) {
  return axios.post(`${API_BASE}/api/tasks`, task);
}
export function completeTask(id) {
  return axios.put(`${API_BASE}/api/tasks/${id}/complete`);
}