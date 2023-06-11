import React, {useState} from "react";
import {Checkbox, IconButton, InputBase, ListItem, ListItemSecondaryAction, ListItemText} from "@mui/material";
import {DeleteOutlined} from "@mui/icons-material";

const Todo = (props) => {
    const [item, setItem] = useState(props.item);

    return(
        <ListItem>
            <Checkbox checked={item.done} />
            <ListItemText>
                <InputBase
                    inputProps={{"aria-label": "naked"}}
                    type="text"
                    id={item.id}
                    name={item.id}
                    value={item.title}
                    multiline={true}
                    fullWidth={true}
                    />
            </ListItemText>
            <ListItemSecondaryAction>
                <IconButton aria-label="Delete Todo" >
                    <DeleteOutlined />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    );
};

export default Todo;