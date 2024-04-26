import React, { useState, useEffect } from 'react';

function GetRandomJoke() {
    // State to store the joke
    const [joke, setJoke] = useState('');

    // useEffect to fetch the joke
    useEffect(() => {
        const fetchJoke = () => {
            const headers = {"Accept": "application/json"};
            fetch('https://icanhazdadjoke.com/', {headers})
                .then(response => response.json())
                .then(data => setJoke(data.joke))
                .catch(error => console.error('Error fetching joke:', error));
        };

        fetchJoke(); // Fetch a joke immediately

        const timerId = setInterval(fetchJoke, 10000); // Fetch a new joke every 10 seconds

        return () => clearInterval(timerId); // Clear the interval when the component unmounts
    }, []);

    return (
        <div>
            <p>{joke}</p>
        </div>
    );
}

export default GetRandomJoke;