import { BrowserRouter, Routes, Route } from 'react-router-dom';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<div>Начална страница (Dashboard)</div>} />
                <Route path="/clients" element={<div>Списък с клиенти</div>} />
                <Route path="/credits" element={<div>Кредити и Погасителни планове</div>} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;