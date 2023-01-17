import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import CollectionPage from "./pages/CollectionPage";
import WelcomePage from "./pages/WelcomePage";
import "./App.css";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/c/:id" element={<CollectionPage />} />
        <Route path="/" element={<WelcomePage />} />
      </Routes>
    </Router>
  );
}

export default App;
