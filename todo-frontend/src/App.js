import React from 'react';
import { Container, Typography, Box, AppBar, Toolbar, Button } from '@mui/material';
import NewTaskForm from './components/NewTaskForm';
import TaskList from './components/TaskList';

export default function App() {
  return (
    <Box sx={{ minHeight: '100vh', bgcolor: 'background.default' }}>
      <AppBar position="static" color="primary" elevation={4}>
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            To Do List App
          </Typography>
        </Toolbar>
      </AppBar>
      <Container maxWidth="sm" sx={{ mt: 4, pb: 4 }}>
        <Typography variant="h4" align="center" gutterBottom sx={{ fontWeight: 500 }}>
          Boost Your Productivity Today!
        </Typography>
        <NewTaskForm onTaskCreated={() => window.location.reload()} />
        <TaskList />
      </Container>
    </Box>
  );
}