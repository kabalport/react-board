import React from 'react';

// 라우터
import {BrowserRouter, Route, Routes} from 'react-router-dom';

// 페이지
import Home from "./routes/Home";

// 부트스트랩



const App = (props) => {
    return (
        <React.Fragment>
            <BrowserRouter>
            <Routes>
                    <Route path='/' component={Home} />
            </Routes>
            </BrowserRouter>
        </React.Fragment>
    )
};

export default App;