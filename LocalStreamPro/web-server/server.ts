// LocalStream Pro Full Node.js / Express Streaming Server
import express from 'express';
const app = express();
app.get('/api/status', (req, res) => res.json({ status: 'ok', server: 'LocalStream Pro' }));
app.listen(8080, () => console.log('Server active on 8080'));
