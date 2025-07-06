import React, { useState } from 'react';
import { createTask } from '../api/tasks';
import { Paper, Box, TextField, Button, useTheme } from '@mui/material';
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';

export default function NewTaskForm({ onTaskCreated }) {
  const theme = useTheme();
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!title.trim()) return;
    try {
      await createTask({ title, description });
      setTitle('');
      setDescription('');
      onTaskCreated();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <Paper elevation={4} sx={{ p: 3, mb: 3, backgroundColor: theme.palette.background.paper, borderRadius: 2 }}>
      <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
        <TextField
          label="Task Title"
          variant="outlined"
          fullWidth
          required
          value={title}
          onChange={e => setTitle(e.target.value)}
        />
        <TextField
          label="Description"
          variant="outlined"
          fullWidth
          multiline
          rows={4}
          value={description}
          onChange={e => setDescription(e.target.value)}
        />
        <Button
          type="submit"
          variant="contained"
          color="primary"
          startIcon={<AddCircleOutlineIcon />}
          sx={{ alignSelf: 'flex-end', textTransform: 'none' }}
        >
          Add New Task
        </Button>
      </Box>
    </Paper>
  );
}