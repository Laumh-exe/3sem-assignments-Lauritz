import React, { useEffect, useState } from 'react';

function GetCountry({ country }) {
  console.log(country)
  const [countryData, setCountryData] = useState(null);
  const url = 'https://restcountries.com/v3.1/alpha/'+country;

  useEffect(() => {
    fetch(url)
      .then(response => response.json())
      .then(data => setCountryData(data[0]))
      .catch(error => console.error('Error fetching data:', error));
  }, [country]);

  if (!countryData) {
    return <p>Country not found</p>;
  }

  return (
    <>
      <h2>{countryData.name.common}</h2>
      <p>Population: {countryData.population}</p>
      <p>Area: {countryData.area} km²</p>
    </>
  );
}

export default GetCountry;