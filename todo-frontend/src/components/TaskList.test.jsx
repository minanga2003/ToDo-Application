import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import TaskList from './TaskList';
import * as api from '../api/tasks';

jest.mock('../api/tasks');

const tasksMock = [
  { id: 1, title: 'A', description: 'desc', completed: false },
  { id: 2, title: 'B', description: '', completed: false }
];

test('renders tasks and marks complete', async () => {
  api.getLatestTasks.mockResolvedValue({ data: tasksMock });
  api.completeTask.mockResolvedValue({});

  render(<TaskList />);

  
  await waitFor(() => screen.getByText('A'));
  expect(screen.getByText('A')).toBeInTheDocument();
  expect(screen.getByText('B')).toBeInTheDocument();

 
  fireEvent.click(screen.getAllByRole('button', { name: /Mark Complete/i })[0]);
  
  expect(api.completeTask).toHaveBeenCalledWith(1);
});
