import React from 'react';
import {cleanup, screen, fireEvent, render} from '@testing-library/react';
import CreateMatch from './CreateMatch';


// POST fetch mock
global.fetch = jest.fn(() =>
  Promise.resolve({
    json: () => Promise.resolve({ rates: { CAD: 1.42 } }),
  })
);
// alert mock
global.window.alert = jest.fn();


// test setup
beforeEach(() => {
    fetch.mockClear();
    window.alert.mockClear();
});
afterEach(cleanup);


it('Post Create Form', () => {

    const result = render(<CreateMatch/>);

    // form inputs
    const nameInput = result.container.querySelector('#name');
    const locationInput = result.container.querySelector('#location');
    const dateInput = result.container.querySelector('#date');
    const timeInput = result.container.querySelector('#time');

   
    // fill out the form
    fireEvent.change(nameInput, {target: {value: 'My match'},})
    fireEvent.change(locationInput, {target: {value: 'My location'},})
    fireEvent.change(dateInput, {target: {value: '2030-01-01'},})
    fireEvent.change(timeInput, {target: {value: '00:00:00'},})


    // check form inputs were filled
    expect(nameInput.value).toBe("My match")
    expect(locationInput.value).toBe("My location")
    expect(dateInput.value).toBe("2030-01-01")
    expect(timeInput.value).toBe("00:00:00")


    // click on 'Create Match' button (submit form)
    const button = screen.getByText(/Crear Partido/i)
    fireEvent.click(button)

    const expectedBody = JSON.stringify({
        name: nameInput.value,
        location: locationInput.value,
        date: dateInput.value,
        hour: timeInput.value,
        userId: "00000000-0000-0000-0000-000000000000"
    })

    expect(fetch).toHaveBeenCalledTimes(1);
    expect(fetch).toHaveBeenCalledWith(
        'http://localhost:8081/matches',
        expect.objectContaining({method: 'POST', body: expectedBody,})
    )
});