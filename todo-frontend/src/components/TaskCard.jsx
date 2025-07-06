import React from 'react';
import { Card, CardContent, Typography, CardActions, Button, useTheme } from '@mui/material';
import CheckCircleOutlineIcon from '@mui/icons-material/CheckCircleOutline';
import { green } from '@mui/material/colors';

export default function TaskCard({ task, onComplete }) {
  const theme = useTheme();
  return (
    <Card
      elevation={3}
      sx={{ mb: 2, borderRadius: 2, boxShadow: `0 3px 10px ${theme.palette.divider}` }}
    >
      <CardContent>
        <Typography variant="h6" sx={{ fontWeight: 600, mb: 1 }}>
          {task.title}
        </Typography>
        {task.description && (
          <Typography variant="body2" color="text.secondary" sx={{ mb: 2 }}>
            {task.description}
          </Typography>
        )}
        <CardActions sx={{ justifyContent: 'flex-end' }}>
          <Button
            variant="outlined"
            color="success"
            startIcon={<CheckCircleOutlineIcon />}
            onClick={() => onComplete(task.id)}
            sx={{ textTransform: 'none' }}
          >
            Mark Complete
          </Button>
        </CardActions>
      </CardContent>
    </Card>
  );
}