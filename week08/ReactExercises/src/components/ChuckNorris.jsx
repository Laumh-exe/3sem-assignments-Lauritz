import React, { useState, useEffect } from 'react';

function GetJoke() {
    // State to store the joke
    const [joke, setJoke] = useState('');
    const [buttonClick, setButtonClick] = useState(0);

    // useEffect to fetch the joke
    useEffect(() => {
        fetch('https://api.chucknorris.io/jokes/random')
            .then(response => response.json())
            .then(data => setJoke(data.value))
            .catch(error => console.error('Error fetching joke:', error));
    }, [buttonClick]); // Fetches a new joke every time buttonClick changes

    // Function to handle button click
    const handleClick = () => {
        setButtonClick(prevCount => prevCount + 1); // Increment buttonClick
    };

    // Render the joke
    return (
        <div>
            <p>{joke}</p>
            <button onClick={handleClick}>Get Joke</button>
        </div>
    );
}

export default GetJoke;