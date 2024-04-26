import {useState, useEffect} from 'react'
import map from './assets/countries_europe.svg'
import viteLogo from '/vite.svg'
import './App.css'
import SvgComponent from './CountriesEurope'
import GetCountry from './getCountry'

function App() {
  const [lastClicked, setLastClicked] = useState(null);
  const [currentCountry, setCurrentCountry] = useState(null);

  const handleCountryClick = (target) => {
    if (lastClicked) {
      lastClicked.setAttribute("fill", "silver");
    }
    console.log("Country clicked: ", target.id);
    target.setAttribute("fill", "red");
    setLastClicked(target)
    setCurrentCountry(target.id)
    //Call on compoonent that shows details of country
  };

  return (
    <>
      <div>
        <SvgComponent className="map" onCountryClick={handleCountryClick} />
        <h1>
          Country
        </h1>
        <>
          <GetCountry country={currentCountry} />
        </>
      </div>
    </>
  )
}

export default App
