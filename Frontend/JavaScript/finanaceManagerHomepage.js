/*
    check login status
*/
window.addEventListener('load', async () => {

    console.log('index.checkLoginStatus executed');

    let res = await fetch('http://127.0.0.1:8080/checkloginstatus', {
        method: 'GET',
        credentials: 'include' 
    });

    // 200 code = already logged in
    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'employee') {
            window.location.href = '/HTML/employeeHomepage.html';
        } 
    } else if (res.status === 401) {
        window.location.href = '/index.html';
    }

    // retrieve all reimbursement table once sign in
    populateReimbursementTable();
});

/*
    logout btn
*/
let logoutBtn = document.querySelector('#logout-btn');

logoutBtn.addEventListener('click', async () => {

    let res = await fetch('http://127.0.0.1:8080/logout', {
        'method' : 'POST',
        'credentials' : 'include'
    });

    if (res.status === 200) {
        window.location.href = '/index.html';
    }
})

/*
    populate table
*/
async function populateReimbursementTable() {
     
    // get reimbursements
    let res = await fetch('http://127.0.0.1:8080/reimbursements', {
        method: 'GET',
        credentials: 'include'
    });

    // manage table
    let tbodyElement = document.querySelector("#reimbursements-table tbody");
    // clear data in the table
    tbodyElement.innerHTML = '';
    
    let reimbursementArray = await res.json();

    for (let i = 0; i < reimbursementArray.length; i++) {
        let reimbursement = reimbursementArray[i];

        let tr = document.createElement('tr');

        // reimbId
        let tdReimbId = document.createElement('td');
        tdReimbId.innerHTML = reimbursement.reimbId;
    
        // amount
        let tdAmount = document.createElement('td');
        tdAmount.innerHTML = reimbursement.amount;
    
        // type
        let tdType = document.createElement('td');
        tdType.innerHTML = reimbursement.type;

        // description
        let tdDesc = document.createElement('td');
        tdDesc.innerHTML = reimbursement.description;

        // receipt
        let tdReceipt = document.createElement('td');
        let viewImageBtn = document.createElement('button');
        viewImageBtn.innerHTML = 'View receipt';
        tdReceipt.appendChild(viewImageBtn);

        viewImageBtn.addEventListener('click', () => {
            /* 
                add the is-active class to the modal (so that the modal appears)
                inside of the modal on div.modal-content (div w/ class modal-content)
                -> img tag with src="http://127.0.0.1:8080/reimbursements/3/receipt"
            */
        let receiptImageModal = document.querySelector('#receipt-image-modal');
        receiptImageModal.style.display = "block";

        // close span
        let modalCloseElement = document.querySelector('.close');
        modalCloseElement.addEventListener('click', () => {
            receiptImageModal.style.display = "none";
        });

            // use querySelector on the element to find the child elements nested within
            let modalContentElement = receiptImageModal.querySelector('.modal-content');
            modalContentElement.innerHTML = ''; // clear data

            let imageElement = document.querySelector('#img');
            imageElement.setAttribute('src', `http://127.0.0.1:8080/reimbursements/${reimbursement.reimbId}/receipt`);
            imageElement.setAttribute('alt', 'Receipt image not found.');

            // add a class to the modal element to have it display
            receiptImageModal.classList.add('is-active');
        });

        let tdStat = document.createElement('td');
        tdStat.innerHTML = reimbursement.status;

        let tdSubTime = document.createElement('td');
        tdSubTime.innerHTML = reimbursement.submittedTime;

        let tdResTime = document.createElement('td');
        tdResTime.innerHTML = reimbursement.resolvedTime;

        let tdAuthId = document.createElement('td');
        tdAuthId.innerHTML = reimbursement.authorId;

        let tdResId = document.createElement('td');
        tdResId.innerHTML = reimbursement.resolverId;

        // change status
        let tdChangeStat = document.createElement('td');
        let tdSubmitStat = document.createElement('td');

        if (reimbursement.resolverId != 0) {
                tdStat.innerHTML = reimbursement.status;
                tdResId.innerHTML = reimbursement.resolverId;
        } else { // if resolverId is 0, display below content
            tdStat.innerHTML = 'Pending';
            tdResId.innerHTML = '-';
        
            // linking each button with a particular parameter (reimbId that want to change the status of)
            let statusSelection = document.createElement('select');

            // option
            let option = document.createElement('option');
            option.innerHTML = '';

            // approved option
            let approvedOption = document.createElement('option');
            approvedOption.setAttribute('value', 'Approved');
            approvedOption.innerHTML = 'Approved';
        
            // denied option
            let deniedOption = document.createElement('option');
            deniedOption.setAttribute('value', 'Denied');
            deniedOption.innerHTML = 'Denied';

            statusSelection.appendChild(option);
            statusSelection.appendChild(approvedOption);
            statusSelection.appendChild(deniedOption);

            // submit button
            let statusBtn = document.createElement('button');
            statusBtn.innerText = 'Submit Change';
            statusBtn.addEventListener('click', async () => {
        
            let res = await fetch(`http://127.0.0.1:8080/reimbursements/${reimbursement.reimbId}`, {
                    method: 'PATCH',
                    credentials: 'include',
                    body: JSON.stringify({
                        status: statusSelection.value
                    })
                });
                        
                if (res.status === 200) {
                    populateReimbursementTable(); // refresh table after update
                } else if (res.status === 401) { // if status code 401 =>
                    let reimbForm = document.querySelector('.message');
            
                    let data = await res.json();
            
                    let pTag = document.createElement('p');
                    pTag.innerHTML = data.message; // return data message
                    pTag.style.color = 'red';
            
                    reimbForm.appendChild(pTag);
                }
            });
        
            tdChangeStat.appendChild(statusSelection);
            tdSubmitStat.appendChild(statusBtn);
            }

        tr.appendChild(tdReimbId);
        tr.appendChild(tdAmount);
        tr.appendChild(tdType);
        tr.appendChild(tdDesc);
        tr.appendChild(tdReceipt);
        tr.appendChild(tdStat);
        tr.appendChild(tdSubTime);
        tr.appendChild(tdResTime);
        tr.appendChild(tdAuthId);
        tr.appendChild(tdResId);
        tr.appendChild(tdChangeStat);
        tr.appendChild(tdSubmitStat);

        tbodyElement.appendChild(tr);
    } 
}

/*
    filter status
*/
let statusFilterBtn = document.querySelector('#status-filter-btn');
statusFilterBtn.addEventListener('click', submitStatusFilterTable);

async function submitStatusFilterTable() {
    let status = document.querySelector('#status-dropdown').value;

    if (status == 'get all') {

        populateReimbursementTable();

    } else {
        let res = await fetch(`http://127.0.0.1:8080/reimbursements/${status}/status`, {
            method: 'GET',
            credentials: 'include',
        });
    
        let tbodyElement = document.querySelector("#reimbursements-table tbody");
        // clear data in the table
        tbodyElement.innerHTML = '';
        
        let reimbursementArray = await res.json();
    
        for (let i = 0; i < reimbursementArray.length; i++) {
            let reimbursement = reimbursementArray[i];
    
            let tr = document.createElement('tr');
    
            // reimbId
            let tdReimbId = document.createElement('td');
            tdReimbId.innerHTML = reimbursement.reimbId;
        
            // amount
            let tdAmount = document.createElement('td');
            tdAmount.innerHTML = reimbursement.amount;
        
            // type
            let tdType = document.createElement('td');
            tdType.innerHTML = reimbursement.type;
    
            // description
            let tdDesc = document.createElement('td');
            tdDesc.innerHTML = reimbursement.description;
    
            // receipt
            let tdReceipt = document.createElement('td');
            let viewImageBtn = document.createElement('button');
            viewImageBtn.innerHTML = 'View receipt';
            tdReceipt.appendChild(viewImageBtn);
    
            viewImageBtn.addEventListener('click', () => {
                /* 
                    add the is-active class to the modal (so that the modal appears)
                    inside of the modal on div.modal-content (div w/ class modal-content)
                    -> img tag with src="http://127.0.0.1:8080/reimbursements/3/receipt"
                */
                let receiptImageModal = document.querySelector('#receipt-image-modal');
    
                //close button
                let modalCloseElement = document.querySelector('button');
                modalCloseElement.addEventListener('click', () => {
                    receiptImageModal.classList.remove('is-active');
                });
    
                // use querySelector on the element to find the child elements nested within
                let modalContentElement = receiptImageModal.querySelector('.modal-content');
                modalContentElement.innerHTML = ''; // clear data
    
                let imageElement = document.querySelector('img');
                imageElement.setAttribute('src', `http://127.0.0.1:8080/reimbursements/${reimbursement.reimbId}/receipt`);
                imageElement.setAttribute('alt', 'Receipt image not found.');
    
                // add a class to the modal element to have it display
                receiptImageModal.classList.add('is-active');
            });
    
            let tdStat = document.createElement('td');
            tdStat.innerHTML = reimbursement.status;
    
            let tdSubTime = document.createElement('td');
            tdSubTime.innerHTML = reimbursement.submittedTime;
    
            let tdResTime = document.createElement('td');
            tdResTime.innerHTML = reimbursement.resolvedTime;
    
            let tdAuthId = document.createElement('td');
            tdAuthId.innerHTML = reimbursement.authorId;
    
            let tdResId = document.createElement('td');
            tdResId.innerHTML = reimbursement.resolverId;
    
            // change status
            let tdChangeStat = document.createElement('td');
            let tdSubmitStat = document.createElement('td');
    
            if (reimbursement.resolverId != 0) {
                    tdStat.innerHTML = reimbursement.status;
                    tdResId.innerHTML = reimbursement.resolverId;
            } else { // if resolverId is 0, display below content
                tdStat.innerHTML = 'Pending';
                tdResId.innerHTML = '-';
            
                // linking each button with a particular parameter (reimbId that want to change the status of)
                let statusSelection = document.createElement('select');
    
                // option
                let option = document.createElement('option');
                option.innerHTML = '';
    
                // approved option
                let approvedOption = document.createElement('option');
                approvedOption.setAttribute('value', 'Approved');
                approvedOption.innerHTML = 'Approved';
            
                // denied option
                let deniedOption = document.createElement('option');
                deniedOption.setAttribute('value', 'Denied');
                deniedOption.innerHTML = 'Denied';
    
                statusSelection.appendChild(option);
                statusSelection.appendChild(approvedOption);
                statusSelection.appendChild(deniedOption);
    
                // submit button
                let statusBtn = document.createElement('button');
                statusBtn.innerText = 'Submit Change';
                statusBtn.addEventListener('click', async () => {
            
                let res = await fetch(`http://127.0.0.1:8080/reimbursements/${reimbursement.reimbId}`, {
                        method: 'PATCH',
                        credentials: 'include',
                        body: JSON.stringify({
                            status: statusSelection.value
                        })
                    });
                            
                    if (res.status === 200) {
                        populateReimbursementTable(); // refresh table after update
                    } else if (res.status === 400) { // if status code 400 =>
                        let reimbForm = document.querySelector('#reimbursement-table');
                
                        let data = await res.json();
                
                        let pTag = document.createElement('p');
                        pTag.innerHTML = data.message; // return data message
                        pTag.style.color = 'red';
                
                        reimbForm.appendChild(pTag);
                    }
                });
            
                tdChangeStat.appendChild(statusSelection);
                tdSubmitStat.appendChild(statusBtn);
                }
    
            tr.appendChild(tdReimbId);
            tr.appendChild(tdAmount);
            tr.appendChild(tdType);
            tr.appendChild(tdDesc);
            tr.appendChild(tdReceipt);
            tr.appendChild(tdStat);
            tr.appendChild(tdSubTime);
            tr.appendChild(tdResTime);
            tr.appendChild(tdAuthId);
            tr.appendChild(tdResId);
            tr.appendChild(tdChangeStat);
            tr.appendChild(tdSubmitStat);
    
            tbodyElement.appendChild(tr);
        }
        
        if (res.status === 200) { // if successfully added reimbursement =>
            submitStatusFilterTable; // refresh the reimbursement table
            
    
        } else if (res.status === 400) { // if status code 400 =>
            let reimbForm = document.querySelector('#filter-status');
    
            let data = await res.json();
    
            let pTag = document.createElement('p');
            pTag.innerHTML = data.message; // return data message
            pTag.style.color = 'red';
    
            reimbForm.appendChild(pTag);
        }

    }
}
