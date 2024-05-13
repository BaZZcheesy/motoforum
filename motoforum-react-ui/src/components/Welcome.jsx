import logo from '../assets/logo.svg';
import '../styles/App.css';
import NavMenu from './MainNav';

function App() {
  return (
    <>
    <NavMenu />
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
          Welcome in the forum
      </header>
    </div>
    </>
  );
}

export default App;
