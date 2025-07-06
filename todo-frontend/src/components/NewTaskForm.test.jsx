import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import NewTaskForm from './NewTaskForm';
import * as api from '../api/tasks';

jest.mock('../api/tasks');

test('submits form and invokes callback', async () => {
  const onCreated = jest.fn();
  api.createTask.mockResolvedValue({ data: {} });
  render(<NewTaskForm onTaskCreated={onCreated} />);

  fireEvent.change(screen.getByLabelText(/Task Title/i), { target: { value: 'Test' } });
  fireEvent.click(screen.getByRole('button', { name: /Add New Task/i }));

  expect(api.createTask).toHaveBeenCalledWith({ title: 'Test', description: '' });
  expect(onCreated).toHaveBeenCalled();
});