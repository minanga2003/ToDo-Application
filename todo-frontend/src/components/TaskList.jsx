import React, { useEffect, useState, useCallback } from 'react';
import { getLatestTasks, completeTask } from '../api/tasks';
import TaskCard from './TaskCard';
import { Stack, Typography } from '@mui/material';

export default function TaskList() {
  const [tasks, setTasks] = useState([]);

  const fetchTasks = useCallback(async () => {
    try {
      const res = await getLatestTasks();
      setTasks(res.data);
    } catch (err) {
      console.error(err);
    }
  }, []);

  useEffect(() => { fetchTasks(); }, [fetchTasks]);

  const handleComplete = async (id) => {
    try {
      await completeTask(id);
      fetchTasks();
    } catch (err) {
      console.error(err);
    }
  };

  if (tasks.length === 0) {
    return (
      <Typography variant="body1" align="center" sx={{ mt: 4, color: 'text.secondary' }}>
        You're all caught up! Add a new task to stay on top of things.
      </Typography>
    );
  }

  return (
    <Stack spacing={2}>
      {tasks.map(task => (
        <TaskCard key={task.id} task={task} onComplete={handleComplete} />
      ))}
    </Stack>
  );
}